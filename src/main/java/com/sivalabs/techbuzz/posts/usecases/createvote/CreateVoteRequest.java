package com.sivalabs.techbuzz.posts.usecases.createvote;

import jakarta.validation.constraints.NotNull;

public record CreateVoteRequest(@NotNull Long postId, @NotNull Integer value) {
}
