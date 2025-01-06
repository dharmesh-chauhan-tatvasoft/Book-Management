package com.example.bookstoreapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import android.widget.DatePicker
import android.widget.RadioButton

class AddBook : AppCompatActivity() {
    private lateinit var bookNameEditText: EditText
    private lateinit var authorNameEditText: EditText
    private lateinit var genreSpinner: Spinner
    private lateinit var fictionRadioGroup: RadioGroup
    private lateinit var datePicker: DatePicker
    private lateinit var ageGroupCheckBoxKids : CheckBox
    private lateinit var ageGroupCheckBoxTeens : CheckBox
    private lateinit var ageGroupCheckBoxAdults : CheckBox
    private lateinit var ageGroupCheckBoxAllAges : CheckBox
    private lateinit var addBookButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)
        setData()
    }

    private fun setData() {
        bookNameEditText = findViewById(R.id.bookNameEditText)
        authorNameEditText = findViewById(R.id.authorNameEditText)
        genreSpinner = findViewById(R.id.genreSpinner)
        fictionRadioGroup = findViewById(R.id.fictionRadioGroup)
        datePicker = findViewById(R.id.datePicker)
        ageGroupCheckBoxKids = findViewById(R.id.ageGroupCheckBoxKids)
        ageGroupCheckBoxTeens = findViewById(R.id.ageGroupCheckBoxTeens)
        ageGroupCheckBoxAdults = findViewById(R.id.ageGroupCheckBoxAdults)
        ageGroupCheckBoxAllAges = findViewById(R.id.ageGroupCheckBoxAllAges)
        addBookButton = findViewById(R.id.addBookButton)

        val genres = arrayOf(getString(R.string.sci_fi), getString(R.string.biography), getString(R.string.romance), getString(R.string.thriller))
        val genreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genreSpinner.adapter = genreAdapter

        addBookButton.setOnClickListener {
            if(validateBookData()) {
                handleAddBookButton()
            }
        }
    }

    private fun validateBookData(): Boolean {
        if (bookNameEditText.text.isBlank()) {
            Toast.makeText(this, getString(R.string.addBookNameError), Toast.LENGTH_SHORT).show()
            return false
        } else if (authorNameEditText.text.isBlank()) {
            Toast.makeText(this, getString(R.string.addBookAuthorNameError), Toast.LENGTH_SHORT).show()
            return false
        } else if (genreSpinner.selectedItem === null) {
            Toast.makeText(this, getString(R.string.addBookGenreError), Toast.LENGTH_SHORT).show()
            return false
        } else if (fictionRadioGroup.checkedRadioButtonId == -1) {
            Toast.makeText(this, getString(R.string.addBookTypeError), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun handleAddBookButton() {
        val bookName = bookNameEditText.text.toString()
        val authorName = authorNameEditText.text.toString()
        val genre = genreSpinner.selectedItem.toString()
        val bookType = findViewById<RadioButton>(fictionRadioGroup.checkedRadioButtonId).text.toString()

        val launchDate = "${datePicker.dayOfMonth}-${datePicker.month + 1}-${datePicker.year}"
        var ageGroupType = ""
        if (ageGroupCheckBoxKids.isChecked) {
            ageGroupType += getString(R.string.kids)
        }
        if (ageGroupCheckBoxTeens.isChecked) {
            ageGroupType = ageGroupType + "," + getString(R.string.teens)
        }
        if (ageGroupCheckBoxAdults.isChecked) {
            ageGroupType = ageGroupType + "," + getString(R.string.adults)
        }
        if (ageGroupCheckBoxAllAges.isChecked) {
            ageGroupType = ageGroupType + "," + getString(R.string.all_ages)
        }

        val newBook = Book(bookName, authorName, genre, bookType, launchDate, ageGroupType)
        BookDatabase.addBook(newBook)
        Toast.makeText(this, getString(R.string.addBookSuccess), Toast.LENGTH_LONG).show()
        val navigateToBookList = Intent(this, BookList::class.java)
        startActivity(navigateToBookList)
        finish()
    }
}