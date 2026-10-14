import React, { useState } from 'react';
import {
  BrowserRouter,
  Routes,
  Route,
  useLocation,
  Link,
} from 'react-router-dom';

import Home from './Pages/Home';
import Login from './Pages/Login';
import Register from './Pages/Register';
import Cart from './Pages/Cart';
import './App.css';

function AppLayout({ darkMode, toggleTheme, cart, cartCount, addToCart, removeFromCart, updateQuantity,}) 
{
  const location = useLocation();
  const isHomePage = location.pathname === '/';

  return (
    <>
      {!isHomePage && (
        <nav className="navbar">
          <Link to="/" className="logo">
            ShopEase
          </Link>
        </nav>
      )}

      <Routes>
        <Route
          path="/"
          element={
            <Home
              cartCount={cartCount}
              addToCart={addToCart}
              darkMode={darkMode}
              toggleTheme={toggleTheme}
            />
          }
        />

        <Route path="/login" element={<Login />} />

        <Route path="/register" element={<Register />} />

        <Route
          path="/cart"
          element={
            <Cart
              cart={cart}
              removeFromCart={removeFromCart}
              updateQuantity={updateQuantity}
            />
          }
        />
      </Routes>
    </>
  );
}

function App() {
  const [darkMode, setDarkMode] = useState(false);
  const [cart, setCart] = useState([]);

  const toggleTheme = () => {
    setDarkMode((previousMode) => !previousMode);
  };

  const addToCart = (product) => {
    setCart((previousCart) => {
      const existingProduct = previousCart.find(
        (item) => item.id === product.id
      );

      if (existingProduct) {
        return previousCart.map((item) =>
          item.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      }

      return [...previousCart, { ...product, quantity: 1 }];
    });
  };

  const removeFromCart = (productId) => {
    setCart((previousCart) =>
      previousCart.filter((item) => item.id !== productId)
    );
  };

  const updateQuantity = (productId, quantity) => {
    if (quantity < 1) {
      removeFromCart(productId);
      return;
    }

    setCart((previousCart) =>
      previousCart.map((item) =>
        item.id === productId
          ? { ...item, quantity }
          : item
      )
    );
  };

  const cartCount = cart.reduce(
    (total, item) => total + item.quantity,
    0
  );

  return (
    <div className={`app ${darkMode ? 'dark-mode' : 'light-mode'}`}>
      <BrowserRouter>
        <AppLayout
          darkMode={darkMode}
          toggleTheme={toggleTheme}
          cart={cart}
          cartCount={cartCount}
          addToCart={addToCart}
          removeFromCart={removeFromCart}
          updateQuantity={updateQuantity}
        />
      </BrowserRouter>
    </div>
  );
}

export default App;
