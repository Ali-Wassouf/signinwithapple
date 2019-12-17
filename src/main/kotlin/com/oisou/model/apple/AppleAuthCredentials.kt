package com.oisou.model.apple

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "The response body to be sent from the client for the verification")
data class AppleAuthCredentials(
    @ApiModelProperty(notes = "The userId provided by the auth provider")
    val user: String,
    @ApiModelProperty(notes = "The identityToken provided by the auth provider")
    val identityToken: String
)