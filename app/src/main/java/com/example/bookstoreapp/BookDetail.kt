package com.example.bookstoreapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class BookDetail : AppCompatActivity() {
    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        setBookDetailData()
    }

    private fun setBookDetailData() {
        supportActionBar?.title = getString(R.string.book_detail_title)
        book = intent.getParcelableExtra(Constants.BOOK_DETAIL)

        val bookNameTextView: TextView = findViewById(R.id.textViewBookName)
        val authorNameTextView: TextView = findViewById(R.id.textViewAuthorName)
        val genreTextView: TextView = findViewById(R.id.textViewGenre)
        val fictionTextView: TextView = findViewById(R.id.textViewFiction)
        val launchDateTextView: TextView = findViewById(R.id.textViewLaunchDate)
        val ageGroupTextView: TextView = findViewById(R.id.textViewAgeGroup)

        bookNameTextView.text = book?.name
        authorNameTextView.text = book?.author
        genreTextView.text = book?.genre
        fictionTextView.text = if(book?.isFiction == Constants.FICTION) getString(R.string.yes) else getString(R.string.no)
        launchDateTextView.text = book?.launchDate
        ageGroupTextView.text = if(book?.selectedBookAgeGroup != "") book?.selectedBookAgeGroup else getString(R.string.not_available)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_book, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val editBookNavigation = Intent(this, AddBook::class.java).apply {
            putExtra(Constants.BOOK_DETAIL, book)
        }
        startActivity(editBookNavigation)
        finish()
        return true
    }
}