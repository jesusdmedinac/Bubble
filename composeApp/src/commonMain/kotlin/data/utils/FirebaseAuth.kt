package data.utils

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.database.FirebaseDatabase

class FirebaseUtils(
    val auth: FirebaseAuth,
    val database: FirebaseDatabase
) {
    fun getCurrentUserChild() = auth
        .currentUser
        ?.uid
        ?.let { database.reference().child(it) }
}
