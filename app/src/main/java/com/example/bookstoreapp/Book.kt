package com.example.bookstoreapp

import android.os.Parcel
import android.os.Parcelable

data class Book(
    val bookId: Int,
    val name: String,
    val author: String,
    val genre: String,
    val isFiction: String,
    val launchDate: String,
    val selectedBookAgeGroup: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(bookId)
        parcel.writeString(name)
        parcel.writeString(author)
        parcel.writeString(genre)
        parcel.writeString(isFiction)
        parcel.writeString(launchDate)
        parcel.writeString(selectedBookAgeGroup)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}

object BookDatabase {
    private val books = mutableListOf<Book>()

    fun addBook(book: Book) {
        books.add(book)
    }

    fun getBooks(): List<Book> {
        return books
    }

    fun updateBook(updateBook: Book) {
        val index = books.indexOfFirst { it.bookId == updateBook.bookId }
        if (index != -1) {
            books[index] = updateBook
        }
    }
}
