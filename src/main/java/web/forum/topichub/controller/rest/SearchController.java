package web.forum.topichub.controller.rest;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.services.interfaces.*;
import web.forum.topichub.util.*;

import java.util.*;


/**
 * REST Controller for searching articles.
 * Provides an endpoint for searching articles based on various query parameters.
 *
 * <p>This controller allows users to search for articles by providing search parameters.
 * It uses the provided parameters to query articles and return the relevant results.
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final IArticleService articleService;

    /**
     * Searches for articles based on query parameters.
     *
     * <p>This endpoint allows users to search for articles by passing various search parameters.
     * The parameters are parsed into a search DTO, which is then used to query the article service.
     *
     * @param reqParam the request parameters used for the search, such as filters and keywords.
     * @return a ResponseEntity containing the search results in an ArticleBatchDto with a 200 OK status.
     * @see SearchDto
     */
    @GetMapping("")
    public ResponseEntity<?> doGet(
            @RequestParam Map<String, String> reqParam
    ) {
        SearchDto searchDto = HttpRequestUtils.parseSearchParams(reqParam);
        PageResponse<ArticleDto> articleBatchDto = articleService.search(searchDto);
        return new ResponseEntity<>(articleBatchDto, HttpStatus.OK);
    }
}

