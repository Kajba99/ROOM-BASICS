package com.example.roombasics.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roombasics.R
import com.example.roombasics.data.UserViewModel
import com.example.roombasics.usermodel.User
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {


    // klasa koja je automatski generirana kad smo kreirali argument za updateFragment
    // samo ce otvarati ime, prezime i godine vec postojecih clanova baze podataka
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.updateFirstName_et.setText(args.currentUser.firstName)
        view.updateLastName_et.setText(args.currentUser.lastName)
        view.updateAge_et.setText(args.currentUser.age.toString())

        view.update_btn.setOnClickListener {
           updateItem()
          }

        setHasOptionsMenu(true)
        return view
    }

    private fun updateItem() {
        val firstName = updateFirstName_et.text.toString()
        val lastName = updateLastName_et.text.toString()
        val age = Integer.parseInt(updateAge_et.text.toString())

       if (inputCheck(firstName, lastName, updateAge_et.text)) {
           val updatedUser = User(args.currentUser.id, firstName, lastName, age)
           mUserViewModel.updateUser(updatedUser)
           Toast.makeText(requireContext(), "Successfully added!" , Toast.LENGTH_LONG).show()
           findNavController().navigate(R.id.action_updateFragment_to_listFragment)
       } else {
           Toast.makeText(requireContext(), "Please fill out all fields" , Toast.LENGTH_LONG).show()
       }
    }


    private fun inputCheck(firstName : String, lastName : String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "Successfully deleted: ${args.currentUser.firstName}" , Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No"){ _, _ ->}
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
        builder.create().show()
    }

}