import { configureStore } from "@reduxjs/toolkit";
import createSagaMiddleware from "redux-saga"
import { RootSaga } from "../ConnectionSagas/RootSaga";

const sagaMiddleware = createSagaMiddleware()

const store = configureStore({
    reducer:{

    },
    middleware: (getDefaultMiddleware) => {
        getDefaultMiddleware().concat(sagaMiddleware)
    }
})

sagaMiddleware.run(RootSaga);

export default store;