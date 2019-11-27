package com.oisou.applesignin.model

data class AppleAuthPublicKey(
    val kty: String, // The key type parameter setting. This must be set to "RSA".
    val kid: String, // A 10-character identifier key, obtained from your apple account.
    val use: String, // The intended use for the public key.
    val alg: String, // The encryption algorithm used to encrypt the token.
    val n: String, // The modulus value for the RSA public key.
    val e: String // The exponent value for the RSA public key.
)