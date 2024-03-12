    import { createSlice } from "@reduxjs/toolkit";

    const BooksReducer = createSlice({
    name: "Books",
    initialState: {
        data: [],
        totalPages: "",
        loading: false,
        searchInp: "",
        selectVal: 0,
        page: 1,
        modalVisible: false,
        currentItm: "",
        savedFile: "",
        hasFile: "",
        pageSize: 6,
        options: [],
    },
    reducers: {
        setData: (state, action) => {
        state.data = action.payload;
        },
        setTotalPages: (state, action) => {
        state.totalPages = action.payload;
        },
        setLoading: (state, action) => {
        state.loading = !state.loading;
        },
        setSearchInp: (state, action) => {
        state.searchInp = action.payload;
        },
        setSelectVal: (state, action) => {
        state.selectVal = action.payload;
        },
        setPage: (state, action) => {
        state.page = action.payload;
        },
        setModalVisible: (state, action) => {
        state.modalVisible = !state.modalVisible;
        },
        setCurrentItm: (state, action) => {
        state.currentItm = action.payload;
        },
        setSavedFile: (state, action) => {
        state.savedFile = action.payload;
        },
        setHasFile: (state, action) => {
        state.hasFile = action.payload;
        },
        setPageSize: (state, action) => {
        state.pageSize = action.payload;
        },
        handleSave: (state, action) => {},
        getBooks: (state, action) => {},
        setOptions: (state, action) => {
        state.options = action.payload;
        },
        handleSave: (state, action) => {
          
        },
        delItem: () => {},
        editItm: (state, action) => {
        },
        handleFile:()=>{}
    },
    });

    export const BooksActions = { ...BooksReducer.actions };
    export default BooksReducer.reducer;
