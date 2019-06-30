import {applyMiddleware, createStore} from 'redux';
import {createLogger} from 'redux-logger'
import {composeWithDevTools} from 'redux-devtools-extension/developmentOnly';
import {promiseMiddleware, localStorageMiddleware} from './middleware';
import createRootReducer from './reducer';

import {routerMiddleware} from 'connected-react-router';
import {createBrowserHistory} from 'history';

export const history = createBrowserHistory();

// Build the middleware for intercepting and dispatching navigation actions
const myRouterMiddleware = routerMiddleware(history);

export const middlewares = [
    myRouterMiddleware,
    promiseMiddleware,
    localStorageMiddleware
];

const getMiddleware = () => {
    if (process.env.NODE_ENV === 'production') {
        return applyMiddleware(...middlewares);
    } else {
        // Enable additional logging in non-production environments.
        return applyMiddleware(...middlewares, createLogger())
    }
};

export const store = createStore(
    createRootReducer(history),
    composeWithDevTools(getMiddleware())
);
