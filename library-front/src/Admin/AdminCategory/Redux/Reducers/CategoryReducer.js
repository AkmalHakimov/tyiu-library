import {createSlice} from "@reduxjs/toolkit"

const categoryReducer = createSlice({
    name: "category",
    initialState:{
        dataSource: [],
        isModalOpen: false,
        categoryInp: "",
        loading: false,
        currentItm: "",
        totalPages: "",
        page: 1,
        searchInp: "",
        categories: []
    },
    reducers:{
        setDataSource:(state,action)=>{
            state.dataSource = action.payload;
        },
        setIsModal:(state,action)=>{
            state.isModalOpen = !state.isModalOpen;
        },
        setCategoryInp:(state,action)=>{
            state.categoryInp = action.payload
        },
        setIsLoading:(state,action)=>{
            state.loading = !state.loading
        },
        setCurrentItm:(state,action)=>{
            state.currentItm = action.payload
        },
        setTotalPages:(state,action)=>{
            state.totalPages = action.payload
        },
        setPage:(state,action)=>{
            state.page = action.payload
        },
        setSearchInp:(state,action)=>{
            state.searchInp = action.payload
        },
        getCategories:()=>{

        },
        getAllCategories:()=>{

        },
        handleDelete:()=>{

        },
        handleEdit:()=>{

        },
        handleSave:()=>{

        },
        handleDel:()=>{

        },
        handleEdit:()=>{
            
        },
        setCategories:(state,action)=>{
            state.categories = action.payload
        }
    }
})

export const categoryActions = {...categoryReducer.actions};
export default categoryReducer.reducer;