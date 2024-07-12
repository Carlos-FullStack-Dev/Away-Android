package com.plum.networkk.awmapplication.constants
class URLs {

    companion object{
        const val MY_PREFERENCES = "prefs_awayapp"

        // PRODUCTION MODE
        private const val ROOT_URL = "https://app.awaybusiness.com/api"
        private const val ROOT_URL_WO_PATH = "https://app.awaybusiness.com/"

        const val LOGIN = ROOT_URL + "/auth/login"
        const val REGISTRATION = ROOT_URL + "/auth/register"

        const val SEND_STATUS = ROOT_URL + "/v2/sendStatus"
        const val JOIN_COMPANY = ROOT_URL + "/add-join-company"
//        const val JOIN_COMPANY = ROOT_URL + "/v2/add-join-company"
        const val EMPLOYEE_COMPANY = ROOT_URL + "/v2/getCompanies"
        const val STATISTICS = ROOT_URL + "/v2/statistics"
        const val UPDATE_DEVICE_LOGS = ROOT_URL + "/v2/devicelogs"

        const val GET_ALL_EMPLOYEES = ROOT_URL + "/v2/getEmployees"
        const val GET_EMPLOYEE_DETAIL = ROOT_URL + "/v2/getEmployeeDetail"
        const val COMPANY_STATISTICS = ROOT_URL + "/v2/getEmployeesStatistics"

        const val CREATE_COMPANY = ROOT_URL + "/companies/create"
//        const val UPDATE_DEVICE_LOGS = ROOT_URL + "/device-logs"
        const val EMPLOYER_COMPANY = ROOT_URL + "/companies"
        const val FAQS = ROOT_URL + "/faq"

//        const val STATISTICS = ROOT_URL + "/statistics"
        const val GET_EMPLOYEES = ROOT_URL + "/get-employee-against-business-manager"
        const val USER_STATUS = ROOT_URL + "/checkUser"
        const val REMOVE_EMPLOYEE = ROOT_URL + "/employee/delete/"
    }
}