import {createSlice} from "@reduxjs/toolkit"

const AdabiyotlarReducer = createSlice({
    name:"Adabitotlar",
    initialState:{
        data: [],
        totalPages:"",
        loading: false,
        searchInp:"",
        selectVal: 0,
        page: 1,
        modalVisible: false,
        currentItm: "",
        savedFile: "",
        hasFile: "",
        pageSize: 6,
        options: [
            { label: "Yo'nalishlar", value: "" },
        ]
    },
    reducers:{
       setData:(state,action)=>{
            state.data = action.payload
       },
       setTotalPages:(state,action)=>{
        state.totalPages = action.payload
       },
       setLoading:(state,action)=>{
        state.loading = !state.loading
       },
       setSearrchInp:(state,action)=>{
        state.searchInp = action.payload
       },
       setSelectVal:(state,action)=>{
        state.selectVal = action.payload
       },
       setPage:(state,action)=>{
        state.page = action.payload
       },
       setModalVisible:(state,action)=>{
        state.modalVisible = !state.modalVisible
       },
       setCurrentItm:(state,action)=>{
        state.currentItm = action.payload
       },
       setSavedFile:(state,action)=>{
        state.savedFile = action.payload
       },
       setHasFile:(state,action)=>{
        state.hasFile = action.payload
       },
       setPageSize:(state,action)=>{
        state.pageSize = action.payload
       },
       handleSave:(state,action)=>{

       },
       getAdabiyotlar:(state,action)=>{
        
       }

    }
})

export const AdabiyotlarActions = {...AdabiyotlarReducer.actions};
export default AdabiyotlarReducer.reducer;