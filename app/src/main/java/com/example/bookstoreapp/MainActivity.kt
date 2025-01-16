package com.example.bookstoreapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.book_store)
        handleButtonListener()
    }

    private fun handleButtonListener() {
        val addBook: Button = findViewById(R.id.addBook)
        val bookList: Button = findViewById(R.id.bookList)
        addBook.setOnClickListener {
            navigationToAddBook()
        }
        bookList.setOnClickListener {
            navigationToBookList()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.book_list -> {
                navigationToBookList()
            }
            R.id.add_book -> {
                navigationToAddBook()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun navigationToBookList() {
        val navigateToBookList = Intent(this, BookList::class.java)
        startActivity((navigateToBookList))
    }

    private fun navigationToAddBook() {
        val intentForAddBook = Intent(this, AddBook::class.java)
        startActivity(intentForAddBook)
    }
}