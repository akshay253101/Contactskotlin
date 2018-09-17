package com.example.contacts.kotlin.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Class which provides a model for post
 * @constructor Sets all properties of the post
 * @property contactId the unique identifier of the author of the post
 * @property firsName first name of contact
 * @property lastName last name of contact
 * @property email the email of contact
 * @property phone phone number of contact
 * @property notes notes for contact
 */
@Entity
data class Post(@PrimaryKey
                val contactId: Int,
                val firsName: String,
                val lastName: String,
                val email: String,
                val phone: String,
                val notes: String
)