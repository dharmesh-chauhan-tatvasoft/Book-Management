package com.example.bookstoreapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class BookList: AppCompatActivity() {
    private lateinit var recyclerList: RecyclerView
    private lateinit var bookListAdapter: BookListAdapter
    private var books = BookDatabase.getBooks()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)
        handleAdapter()
    }

    private fun handleAdapter() {
        recyclerList = findViewById(R.id.bookListRecyclerView)
        bookListAdapter = BookListAdapter(books) { book ->
            val navigateToBookDetail = Intent(this, BookDetail::class.java).apply {
                putExtra(getString(R.string.book_detail_key), book)
            }
            startActivity(navigateToBookDetail)
        }
        recyclerList.layoutManager = LinearLayoutManager(this)
        recyclerList.adapter = bookListAdapter
    }
}