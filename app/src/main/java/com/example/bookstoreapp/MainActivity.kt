package com.example.bookstoreapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.book_list -> {
                val navigateToBookList = Intent(this, BookList::class.java)
                startActivity((navigateToBookList))
            }
            R.id.add_book -> {
                val intentForAddBook = Intent(this, AddBook::class.java)
                startActivity(intentForAddBook)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}