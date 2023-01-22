package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.usecases.getposts.GetPostsHandler;
import com.sivalabs.techbuzz.posts.usecases.getposts.PostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ViewCategoryController {

	private static final String PAGINATION_PREFIX = "paginationPrefix";

	private final GetPostsHandler getPostsHandler;

	@GetMapping("/c/{category}")
	public String viewCategory(@PathVariable(name = "category") String categorySlug,
			@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) {
		log.info("Fetching posts for category {} with page: {}", categorySlug, page);
		PagedResult<PostDTO> data = getPostsHandler.getPostsByCategorySlug(categorySlug, page);
		if (data.getData().isEmpty() && (page > 1 && page > data.getTotalPages())) {
			return "redirect:/c/" + categorySlug + "?page=" + data.getTotalPages();
		}
		Category category = getPostsHandler.getCategory(categorySlug);

		model.addAttribute("category", category);
		model.addAttribute(PAGINATION_PREFIX, "/c/" + categorySlug + "?");

		model.addAttribute("postsData", data);
		model.addAttribute("categories", getPostsHandler.getAllCategories());
		return "category";
	}

}
