package ru.gavrilovegor519.rssaggregator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilovegor519.rssaggregator.config.TestContainersConfig;
import ru.gavrilovegor519.rssaggregator.dto.input.feed.FeedInputDto;
import ru.gavrilovegor519.rssaggregator.entity.Feed;
import ru.gavrilovegor519.rssaggregator.entity.User;
import ru.gavrilovegor519.rssaggregator.exception.UserNotFoundException;
import ru.gavrilovegor519.rssaggregator.repo.FeedRepo;
import ru.gavrilovegor519.rssaggregator.repo.UserRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FeedControllerIntegrationTest extends TestContainersConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeedRepo feedRepository;

    @Autowired
    private UserRepo userRepository;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        feedRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void testAddFeed() throws Exception {
        createUser("test@example.com", "password");
        FeedInputDto feedInputDto = createFeedInputDto("Test Feed", "https://www.example.com/rss");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/1.0/feed/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedInputDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Feed"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("https://www.example.com/rss"));

        User user = userRepository.findByEmail("test@example.com").orElseThrow(UserNotFoundException::new);

        assertFalse(user.getFeeds().isEmpty());
        assertEquals("Test Feed", user.getFeeds().getFirst().getName());
        assertEquals("https://www.example.com/rss", user.getFeeds().getFirst().getUrl());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void testListFeeds() throws Exception {
        User testUser = createUser("test@example.com", "password");
        createFeed("Test Feed", "https://www.example.com/rss", testUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1.0/feed/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Feed"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").value("https://www.example.com/rss"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void testDeleteFeed() throws Exception {
        User testUser = createUser("test@example.com", "password");
        long feedId = createFeed("Test Feed", "https://www.example.com/rss", testUser).getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/1.0/feed/delete/" + feedId))
                .andExpect(status().isOk());

        assertFalse(feedRepository.findById(feedId).isPresent());
    }

    @ParameterizedTest
    @WithMockUser(username = "user@example.com")
    @CsvSource({"0, 10", "1, 5", "2, 20"})
    public void testGetNewsFromAllFeeds(String page, String size) throws Exception {
        User testUser = createUser("user@example.com", "password");
        createFeed("Test Feed", "https://www.opennet.ru/opennews/opennews_all_noadv.rss", testUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1.0/feed/headlines")
                        .param("page", page)
                        .param("size", size))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }

    private User createUser(String email, String password) {
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        return userRepository.save(user);
    }

    private Feed createFeed(String name, String url, User user) {
        Feed feed = new Feed();
        feed.setName(name);
        feed.setUrl(url);
        feed = feedRepository.save(feed);

        user.getFeeds().add(feed);
        userRepository.save(user);

        return feed;
    }

    private FeedInputDto createFeedInputDto(String name, String url) {
        FeedInputDto feedInputDto = new FeedInputDto();
        feedInputDto.setName(name);
        feedInputDto.setUrl(url);
        return feedInputDto;
    }
}
