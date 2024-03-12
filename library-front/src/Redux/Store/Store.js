import { configureStore } from "@reduxjs/toolkit";
import createSagaMiddleware from "redux-saga"
import { RootSaga } from "../ConnectionSagas/RootSaga";
import CategoryReducer from "../../Admin/AdminCategory/Redux/Reducers/CategoryReducer";
import BooksReducer from "../../Admin/Adabiyotlar/Redux/Reducers/BooksReducer";

const sagaMiddleware = createSagaMiddleware()

const store = configureStore({
    reducer:{
        category: CategoryReducer,
        Books: BooksReducer,
    },
    middleware: (getDefaultMiddleware) => 
        getDefaultMiddleware().concat(sagaMiddleware)
    
})

sagaMiddleware.run(RootSaga);

export default store;