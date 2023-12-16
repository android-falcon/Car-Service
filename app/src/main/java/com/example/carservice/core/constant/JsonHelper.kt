package com.example.carservice.core.constant

import com.example.carservice.feature.services.domain.model.ServiceBodyModel
import org.json.JSONArray
import org.json.JSONObject


class JsonHelper {

    companion object {

        fun addServiceBodyModelToDtlJson(existingJson: String, newServiceBodyModel: ServiceBodyModel): String {
            // Parse existing JSON string into a JSONObject
            val existingJsonObject = if (existingJson.isNotBlank()) JSONObject(existingJson) else JSONObject()

            // Create a new JSONObject for the new ServiceBodyModel
            val newServiceBodyModelJson = JSONObject().apply {
                put("ITEMCODE", newServiceBodyModel.itemCode)
                put("ITEMNAME", newServiceBodyModel.itemName)
            }

            // Get the existing "DTL" array or create a new one if it doesn't exist
            val dtlArray = existingJsonObject.optJSONArray("DTL") ?: JSONArray()

            // Add the new ServiceBodyModel JSON to the array
            dtlArray.put(newServiceBodyModelJson)

            // Update the existing JSON with the modified "DTL" array
            existingJsonObject.put("DTL", dtlArray)

            // Convert the JSONObject back to a String
            return existingJsonObject.toString()
        }
        fun addEmployeeModelToDtlJson(existingJson: String, employee_number: String): String {
            // Parse existing JSON string into a JSONObject
            val existingJsonObject = if (existingJson.isNotBlank()) JSONObject(existingJson) else JSONObject()

            // Create a new JSONObject for the new ServiceBodyModel
            val newServiceBodyModelJson = JSONObject().apply {
                put("EMPLOYEE_NO", employee_number)
            }

            // Get the existing "DTL" array or create a new one if it doesn't exist
            val dtlArray = existingJsonObject.optJSONArray("DTL") ?: JSONArray()

            // Add the new ServiceBodyModel JSON to the array
            dtlArray.put(newServiceBodyModelJson)

            // Update the existing JSON with the modified "DTL" array
            existingJsonObject.put("DTL", dtlArray)

            // Convert the JSONObject back to a String
            return existingJsonObject.toString()
        }
        fun deleteEmployeeModelFromDtlJson(existingJson: String, itemCodeToDelete: String): String {
            // Parse existing JSON string into a JSONObject
            val existingJsonObject = if (existingJson.isNotBlank()) JSONObject(existingJson) else JSONObject()

            // Get the existing "DTL" array or return the original JSON if it doesn't exist
            val dtlArray = existingJsonObject.optJSONArray("DTL") ?: JSONArray()

            // Find the index of the item to delete
            val indexToDelete = findIndexToDelete(dtlArray, itemCodeToDelete)

            if (indexToDelete != -1) {
                // Remove the item at the specified index
                dtlArray.remove(indexToDelete)

                // Update the existing JSON with the modified "DTL" array
                existingJsonObject.put("DTL", dtlArray)
            }

            // Convert the JSONObject back to a String
            return existingJsonObject.toString()
        }

        fun deleteServiceBodyModelFromDtlJson(existingJson: String, itemCodeToDelete: String): String {
            // Parse existing JSON string into a JSONObject
            val existingJsonObject = if (existingJson.isNotBlank()) JSONObject(existingJson) else JSONObject()

            // Get the existing "DTL" array or return the original JSON if it doesn't exist
            val dtlArray = existingJsonObject.optJSONArray("DTL") ?: JSONArray()

            // Find the index of the item to delete
            val indexToDelete = findIndexToDelete(dtlArray, itemCodeToDelete)

            if (indexToDelete != -1) {
                // Remove the item at the specified index
                dtlArray.remove(indexToDelete)

                // Update the existing JSON with the modified "DTL" array
                existingJsonObject.put("DTL", dtlArray)
            }

            // Convert the JSONObject back to a String
            return existingJsonObject.toString()
        }

        private fun findIndexToDelete(dtlArray: JSONArray, itemCodeToDelete: String): Int {
            // Find the index of the item with the specified "ITEMCODE"
            for (i in 0 until dtlArray.length()) {
                val item = dtlArray.getJSONObject(i)
                if (item.optString("ITEMCODE") == itemCodeToDelete) {
                    return i
                }
            }
            return -1
        }
    }
}
