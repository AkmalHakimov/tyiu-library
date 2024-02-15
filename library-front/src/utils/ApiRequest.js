import axios from "axios"

const BASE_URL = "http://localhost:8080" 

export default (url,method,data)=>{
    return axios({
        baseURL: BASE_URL,
        url: "/api" + url,
        method,
        data
    }) 
}