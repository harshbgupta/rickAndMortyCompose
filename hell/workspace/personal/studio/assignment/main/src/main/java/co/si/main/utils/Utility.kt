package co.si.main.utils

import corp.hell.kernel.constants.AppData.mLanguage
import corp.hell.kernel.constants.UserData.mAuthToken
import corp.hell.kernel.constants.UserData.mCountryCode
import corp.hell.kernel.constants.UserData.mFirstName
import corp.hell.kernel.constants.UserData.mLastName
import corp.hell.kernel.constants.UserData.mUserId
import corp.hell.kernel.constants.UserData.mMobileNumber
import corp.hell.kernel.constants.UserData.mProfilePic
import corp.hell.kernel.utils.DataStorePref
import timber.log.Timber


suspend fun getPreBasicDataFromDataStoreAndEstablishSocketConnection() {
    Timber.d("data -> pre data storing starts ==========> ")
    mAuthToken = DataStorePref.readFromDataStore(DataStorePref.AUTH_TOKEN)
    mCountryCode = DataStorePref.readFromDataStore(DataStorePref.DIAL_CODE)
    mMobileNumber = DataStorePref.readFromDataStore(DataStorePref.PHONE_NUMBER)
    mLanguage = DataStorePref.readFromDataStore(DataStorePref.LANGUAGE) ?: "en"
    mUserId = DataStorePref.readFromDataStore(DataStorePref.USER_ID) ?: -1
    mProfilePic = DataStorePref.readFromDataStore(DataStorePref.PROFILE_URL)
    mFirstName = DataStorePref.readFromDataStore(DataStorePref.FULL_NAME)
    mLastName = DataStorePref.readFromDataStore(DataStorePref.FULL_NAME)

    Timber.d("data -> splash authToken $mAuthToken")
    Timber.d("data -> splash coutry code $mCountryCode")
    Timber.d("data -> splash mobileNumber $mMobileNumber")
    Timber.d("data -> splash language $mLanguage")
    Timber.d("data -> splash userId $mUserId")
    Timber.d("data -> splash profilePicUrl $mProfilePic")
    Timber.d("data -> splash first name $mFirstName")
    Timber.d("data -> splash last name $mLastName")

    Timber.d("data -> pre data storing ends ============> ")
}