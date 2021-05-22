package com.example.noteapp.presenter.note

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.noteapp.AutoDisposable
import com.example.noteapp.NoteDB.entity.NoteItem
import com.example.noteapp.R
import com.example.noteapp.presenter.main.HomeViewModel
import com.example.noteapp.repository.HomeRepo

class NoteFragment : Fragment() {

    companion object {
        fun newInstance() = NoteFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var homeRepo: HomeRepo
    lateinit var disposable: AutoDisposable
    private var etTitle: EditText? = null
    private var etContent: EditText? = null
    private var bundle: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        bundle = arguments?.getInt("position")?: -1
            Log.d("notePosition", bundle.toString())
        if (bundle != -1) {
            val noteItem = viewModel.liveDataNoteItem.value?.get(bundle)
            Log.d("note", noteItem.toString())
            noteItem?.let {
                etTitle?.setText(it.title, TextView.BufferType.EDITABLE)
                etContent?.setText(it.content, TextView.BufferType.EDITABLE)
            }
        }

    }

    private fun initView() {
        etTitle = view?.findViewById(R.id.edTitle)
        etContent = view?.findViewById(R.id.edContent)

        disposable = AutoDisposable().also { it.bindTo(lifecycle) }
        homeRepo = HomeRepo.also { it.init(requireContext(), disposable) }
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java).also {
            it.init(homeRepo, disposable)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (bundle == -1) {
            val title = etTitle?.text.toString()
            val content = etContent?.text.toString()

            if (title.trim() != "" || content.trim() != "") {
                viewModel.insertNote(
                    NoteItem(title = title, content = content)
                )
            }
        } else {
            val noteID = viewModel.liveDataNoteItem.value?.get(bundle)?.uid!!
            viewModel.updateNote(
                NoteItem(
                    uid = noteID,
                    title = etTitle?.text.toString(),
                    content = etContent?.text.toString()
                )
            )
        }
    }
}