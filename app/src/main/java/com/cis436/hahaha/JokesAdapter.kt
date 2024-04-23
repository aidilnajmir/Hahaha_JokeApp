package com.cis436.hahaha

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cis436.hahaha.databinding.ItemJokeBinding

class JokesAdapter(private val onRemoveJoke: (Joke) -> Unit)
    : ListAdapter<Joke, JokesAdapter.JokeViewHolder>(DiffCallback()) {

    // Override the onCreateViewHolder to pass the 'onRemoveJoke' callback to the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding = ItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(binding, onRemoveJoke)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position))

        // Check if the current item is the last one
        if (position == itemCount - 1) {
            // Set a bottom margin for the last item
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = 50
            holder.itemView.layoutParams = layoutParams
        } else {
            // Reset the bottom margin for other items
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = 0
            holder.itemView.layoutParams = layoutParams
        }
    }

    // Include callback in the ViewHolder class
    class JokeViewHolder(
        private var binding: ItemJokeBinding,
        private val onRemoveJoke: (Joke) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(joke: Joke) {
            binding.tvJokeContent.text = "\"${joke.contentPart1}\""
            if (joke.contentPart2.isNotEmpty()) {
                val italicizedText = SpannableString("\n\n\"${joke.contentPart2}\"")
                italicizedText.setSpan(StyleSpan(Typeface.ITALIC), 0, italicizedText.length, 0)
                binding.tvJokeContent.append(italicizedText)
            }

            // Set the click listener for the remove button
            binding.btnRemoveJoke.setOnClickListener {
                onRemoveJoke(joke)
            }
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