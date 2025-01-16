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
import java.text.SimpleDateFormat
import java.util.*

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
    private var book: Book? = null
    private var books = BookDatabase.getBooks()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)
        setData()
    }

    private fun setData() {
        supportActionBar?.title = getString(R.string.add_book_title)
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

        if (intent.hasExtra(Constants.BOOK_DETAIL)) {
            book = intent.getParcelableExtra(Constants.BOOK_DETAIL)
            if (book != null) {
                setEditBookData()
            }
        }
    }

    private fun setEditBookData() {
        supportActionBar?.title = getString(R.string.edit_book_title)
        addBookButton.text = getString(R.string.edit_book)
        bookNameEditText.setText(book?.name ?: "")
        authorNameEditText.setText(book?.author ?: "")
        val genres = arrayOf(getString(R.string.sci_fi), getString(R.string.biography), getString(R.string.romance), getString(R.string.thriller))
        val genreIndex = genres.indexOf((book?.genre))
        if (genreIndex != -1) {
            genreSpinner.setSelection(genreIndex)
        }
        for (index in 0 until fictionRadioGroup.childCount) {
            val radioButton = fictionRadioGroup.getChildAt(index) as RadioButton
            if (radioButton.text.toString() == book?.isFiction) {
                fictionRadioGroup.check(radioButton.id)
                break
            }
        }
        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
        val selectedDate = dateFormat.parse(book?.launchDate ?: "")

        val calendar = Calendar.getInstance()
        if (selectedDate != null) {
            calendar.time = selectedDate
        }

        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        ageGroupCheckBoxKids.isChecked =
            book?.selectedBookAgeGroup?.contains(getString(R.string.kids)) ?: false
        ageGroupCheckBoxTeens.isChecked = book?.selectedBookAgeGroup?.contains(getString(R.string.teens)) ?: false
        ageGroupCheckBoxAdults.isChecked = book?.selectedBookAgeGroup?.contains(getString(R.string.adults)) ?: false
        ageGroupCheckBoxAllAges.isChecked = book?.selectedBookAgeGroup?.contains(getString(R.string.all_ages)) ?: false
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
        val ageGroupType = arrayOf<String>()
        val mutableAgeGroupType = ageGroupType.toMutableList()
        if (ageGroupCheckBoxKids.isChecked) {
            mutableAgeGroupType.add(getString(R.string.kids))
        }
        if (ageGroupCheckBoxTeens.isChecked) {
            mutableAgeGroupType.add(getString(R.string.teens))
        }
        if (ageGroupCheckBoxAdults.isChecked) {
            mutableAgeGroupType.add(getString(R.string.adults))
        }
        if (ageGroupCheckBoxAllAges.isChecked) {
            mutableAgeGroupType.add(getString(R.string.all_ages))
        }

        val selectedAgeGroupString = mutableAgeGroupType.joinToString(", ")
        if (book != null) {
            val newBook = Book(
                book?.bookId ?: 1,
                bookName,
                authorName,
                genre,
                bookType,
                launchDate,
                selectedAgeGroupString
            )
            BookDatabase.updateBook(newBook)
            Toast.makeText(this, getString(R.string.updateBookSuccess), Toast.LENGTH_SHORT).show()
        } else {
            val newBook = Book(
                books.size + 1,
                bookName,
                authorName,
                genre,
                bookType,
                launchDate,
                selectedAgeGroupString
            )
            BookDatabase.addBook(newBook)
            Toast.makeText(this, getString(R.string.addBookSuccess), Toast.LENGTH_SHORT).show()
        }
        val navigateToBookList = Intent(this, BookList::class.java)
        navigateToBookList.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(navigateToBookList)
        finish()
    }
}