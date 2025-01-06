package com.example.bookstoreapp
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class BookListAdapter (private var books: List<Book>, private val onBookClick: (Book) -> Unit): RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.booklist_item_row, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.itemView.setOnClickListener { onBookClick(book) }
        holder.bookName.text = book.name
        holder.authorName.text = book.author
    }

    override fun getItemCount(): Int = books.size

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookName: TextView = itemView.findViewById(R.id.bookName)
        val authorName: TextView = itemView.findViewById(R.id.authorName)
    }
}
