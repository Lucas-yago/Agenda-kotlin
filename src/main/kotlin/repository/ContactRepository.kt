package repository

import entity.ContactEntity

class ContactRepository {

    companion object{

        private val contactList = mutableListOf<ContactEntity>()

        fun save (contact: ContactEntity){
            contactList.add(contact)
        }

        fun delete (contact: ContactEntity){
            val targetContact = contactList.find{ it.name == contact.name && it.phone == contact.phone }
            contactList.remove(targetContact)

        }

        fun getList():List<ContactEntity>{
            return contactList
        }
    }


}