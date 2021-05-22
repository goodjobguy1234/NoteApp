package com.example.noteapp.presenter.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.AutoDisposable
import com.example.noteapp.NoteDB.entity.NoteItem
import com.example.noteapp.R
import com.example.noteapp.presenter.NoteItemListAdapter
import com.example.noteapp.repository.HomeRepo

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var rvHome: RecyclerView? = null
    private lateinit var homeRepo: HomeRepo
    lateinit var disposable: AutoDisposable
    val noteItem: ArrayList<NoteItem> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        viewModel.getAllNote()

        rvHome = view?.findViewById(R.id.rvHome)
        rvHome?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = NoteItemListAdapter(noteItem) {
                Log.d("position", it.toString())
                val bundle = bundleOf("position" to it)
                findNavController().navigate(R.id.noteFragment2, bundle)
            }
        }

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val pos: Int = viewHolder.adapterPosition
                viewModel.deleteNote(noteItem[pos])
            }
        }

        ItemTouchHelper(simpleCallback).apply {
            attachToRecyclerView(rvHome)
        }

        observableNote()

    }

    private fun initView() {
        disposable = AutoDisposable().also { it.bindTo(lifecycle) }
        homeRepo = HomeRepo.also { it.init(requireContext(), disposable) }
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java).also {
            it.init(homeRepo, disposable)

        }
    }

    private fun observableNote() {
        viewModel.liveDataNoteItem.observe(viewLifecycleOwner, {
            noteItem.clear()
            noteItem.addAll(it)
            rvHome?.adapter?.notifyDataSetChanged()
        })
    }



}