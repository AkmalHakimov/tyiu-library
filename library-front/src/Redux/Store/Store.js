import { configureStore } from "@reduxjs/toolkit";
import createSagaMiddleware from "redux-saga"
import { RootSaga } from "../ConnectionSagas/RootSaga";
import CategoryReducer from "../../Admin/AdminCategory/Redux/Reducers/CategoryReducer";

const sagaMiddleware = createSagaMiddleware()

const store = configureStore({
    reducer:{
        category: CategoryReducer
    },
    middleware: (getDefaultMiddleware) => 
        getDefaultMiddleware().concat(sagaMiddleware)
    
})

sagaMiddleware.run(RootSaga);

export default store;