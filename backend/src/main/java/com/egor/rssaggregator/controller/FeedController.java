package com.egor.rssaggregator.controller;

import com.egor.rssaggregator.dto.FeedDto;
import com.egor.rssaggregator.dto.NewsEntryDto;
import com.egor.rssaggregator.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/1.0/feed")
@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
public class FeedController {
    private final FeedService feedService;

    @PostMapping("/add")
    @Operation(summary = "Add new feed",
            responses = {
                    @ApiResponse(description = "Feed is added",
                            responseCode = "200"),
                    @ApiResponse(responseCode = "409",
                            description = "Duplicate feed data"),
                    @ApiResponse(responseCode = "403",
                            description = "User is not authenticated")
            })
    public void addFeed(@Parameter(description = "Feed data", required = true)
                            @RequestBody @Valid FeedDto dto,
                        Authentication authentication) {
        feedService.addFeed(dto, authentication.getName());
    }

    @GetMapping("/list")
    @Operation(summary = "List of feeds",
            responses = {
                    @ApiResponse(description = "List of feeds is returned",
                            responseCode = "200"),
                    @ApiResponse(responseCode = "403",
                            description = "User is not authenticated")
            })
    public List<FeedDto> listFeeds(Authentication authentication) {
        return feedService.getFeeds(authentication.getName());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete feed",
            responses = {
                    @ApiResponse(description = "Feed is deleted",
                            responseCode = "200"),
                    @ApiResponse(responseCode = "403",
                            description = "User is not authenticated")
            })
    public void deleteFeed(@Parameter(description = "Feed id", required = true)
                               @PathVariable long id,
                           Authentication authentication) {
        feedService.deleteFeed(id, authentication.getName());
    }

    @GetMapping("/headlines")
    @Operation(summary = "News headlines",
            responses = {
                    @ApiResponse(description = "The list of news headings is returned",
                            responseCode = "200"),
                    @ApiResponse(responseCode = "400",
                            description = "Incorrect request"),
                    @ApiResponse(responseCode = "403",
                            description = "User is not authenticated")
            })
    public Page<NewsEntryDto> getNewsFromAllFeeds(Authentication authentication,
                                                  @Parameter(description = "Page number", required = true)
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @Parameter(description = "Page size", required = true)
                                                  @RequestParam(defaultValue = "10") int size) {
        var pageable = PageRequest.of(page, size);
        return feedService.getNewsHeadings(authentication.getName(), pageable);
    }
}
