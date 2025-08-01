package com.example.blogapp.repository

import com.example.blogapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepositoryImpl: UserRepository {
    val auth: FirebaseAuth= FirebaseAuth.getInstance()
    val database: FirebaseDatabase= FirebaseDatabase.getInstance()
    val ref: DatabaseReference=database.reference.child("users")
    
    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
       auth.signInWithEmailAndPassword(email,password)
           .addOnCompleteListener { res->
               if (res.isSuccessful){
                   callback(true,"Login Successful")
               }
               else{
                   callback(false,"${res.exception?.message}")
               }
           }
    }

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { res->
                if (res.isSuccessful){
                    callback(true,"Registration successfull",
                        "${auth.currentUser?.uid}")
                }
                else{
                    callback(false,"${res.exception?.message}","")
                }
            }
    }

    override fun addUserToDatabase(
        userId: String,
        model: UserModel,
        callBack: (Boolean, String) -> Unit
    ) {
      ref.child(userId).setValue(model).addOnCompleteListener { 
          if (it.isSuccessful) {
              callBack(true, "User successfuly added")
          }
          else{
              callBack(false,"${it.exception?.message}")
              
          }
      }
    }

    override fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { id ->
            if (id.isSuccessful) {
                callback(true, "password reset email sent to $email.")
            } else {
                callback(false, "${id.exception?.message}")

            }
        }
    }

    override fun editProfile(
        userId: String,
        data: MutableMap<String, Any>,
        callBack: (Boolean, String) -> Unit
    ) {
        ref.child(userId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callBack(true,"Profile updated")
            }
            else{
                callBack(true,"${it.exception?.message}")

            }
        
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
       return auth.currentUser
    }

    override fun getUserFromDatabase(
        userId: String,
        callBack: (Boolean, String, UserModel?) -> Unit
    ) {
        ref.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var users=snapshot.getValue(UserModel::class.java)
                    if(users !=null){
                        callBack(true,"User fetched",users)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                callBack(false,error.message,null)
            }

        })
    }

    override fun getAllUsers(callBack: (Boolean, String, List<UserModel?>) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val allUsers = mutableListOf<UserModel>()
                    for (eachUser in snapshot.children) {
                        val user = eachUser.getValue(UserModel::class.java)
                        if (user != null) {
                            allUsers.add(user)
                        }
                    }
                    callBack(true, "Users fetched", allUsers)
                } else {
                    callBack(true, "No users found", emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callBack(false, error.message, emptyList())
            }
        })
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        try {
            auth.signOut()
            callback(true,"Logout Successfully")
        }catch (e: Exception){
            callback(true,"${e.message}")
        }
        
    }

    override fun deleteAccount(
        userId: String,
        callBack: (Boolean, String) -> Unit
    ) {

        ref.child(userId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callBack(true, "User removed")
            }
            else{
                callBack(false,"${it.exception?.message}")

            }
        }
    }
}