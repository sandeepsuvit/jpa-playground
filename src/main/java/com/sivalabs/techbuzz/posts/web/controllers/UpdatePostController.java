package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.usecases.updatepost.UpdatePostHandler;
import com.sivalabs.techbuzz.posts.usecases.updatepost.UpdatePostRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UpdatePostController {

	private static final String MODEL_ATTRIBUTE_POST = "post";

	private final UpdatePostHandler updatePostHandler;

	@GetMapping("/posts/{id}/edit")
	public String editPostForm(@PathVariable Long id, Model model) {
		Post post = updatePostHandler.getPostById(id).orElse(null);
		if (post == null) {
			throw new ResourceNotFoundException("Post not found");
		}
		Long categoryId = null;
		if (post.getCategory() != null) {
			categoryId = post.getCategory().getId();
		}
		UpdatePostRequest updatePostRequest = new UpdatePostRequest(id, post.getTitle(), post.getUrl(),
				post.getContent(), categoryId);

		model.addAttribute(MODEL_ATTRIBUTE_POST, updatePostRequest);
		return "edit-post";
	}

	@PutMapping("/posts/{id}")
	public String updateBookmark(@PathVariable Long id,
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_POST) UpdatePostRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "edit-post";
		}
		Post post = updatePostHandler.getPostById(id).orElse(null);
		if (post == null) {
			throw new ResourceNotFoundException("Post not found");
		}
		var updatePostRequest = new UpdatePostRequest(id, request.title(), request.url(), request.content(),
				request.categoryId());
		Post updatedPost = updatePostHandler.updatePost(updatePostRequest);
		log.info("Post with id: {} updated successfully", updatedPost.getId());
		return "redirect:/c/" + updatedPost.getCategory().getSlug();
	}
}
