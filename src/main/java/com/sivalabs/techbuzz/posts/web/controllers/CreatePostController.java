package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostHandler;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CreatePostController {

	private static final String MODEL_ATTRIBUTE_POST = "post";

	private final CreatePostHandler createPostHandler;

	@GetMapping("/posts/new")
	public String newPostForm(Model model) {
		model.addAttribute(MODEL_ATTRIBUTE_POST, new CreatePostRequest("", "", "", null));
		return "add-post";
	}

	@PostMapping("/posts")
	public String createPost(@Valid @ModelAttribute(MODEL_ATTRIBUTE_POST) CreatePostRequest request,
							 BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "add-post";
		}
		var createPostRequest = new CreatePostRequest(request.title(), request.url(), request.content(),
				request.categoryId());
		Post post = createPostHandler.createPost(createPostRequest);
		log.info("Post saved successfully with id: {}", post.getId());
		redirectAttributes.addFlashAttribute("message", "Post saved successfully");
		return "redirect:/posts/" + post.getId()+"/edit";
	}

}
