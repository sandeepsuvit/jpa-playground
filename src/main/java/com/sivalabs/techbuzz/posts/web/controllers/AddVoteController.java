package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.posts.usecases.createvote.CreateVoteRequest;
import com.sivalabs.techbuzz.posts.usecases.createvote.VoteHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AddVoteController {

	private final VoteHandler voteHandler;

	@PostMapping("/api/votes")
	@ResponseStatus(HttpStatus.OK)
	public void createVote(@Valid @RequestBody CreateVoteRequest request) {
		var createVoteRequest = new CreateVoteRequest(request.postId(), request.value());
		voteHandler.addVote(createVoteRequest);
	}

}
