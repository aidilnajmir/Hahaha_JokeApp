package com.cis436.hahaha

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cis436.hahaha.databinding.ItemJokeBinding

class JokesAdapter : ListAdapter<Joke, JokesAdapter.JokeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding = ItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class JokeViewHolder(private var binding: ItemJokeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(joke: Joke) {
            val context = itemView.context
            binding.jokeContent.text = context.getString(R.string.joke_content, joke.contentPart1, joke.contentPart2)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem == newItem
        }
    }
}