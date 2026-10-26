
import React, { useState } from 'react';
import {
  BrowserRouter,
  Routes,
  Route,
  useLocation,
  Link,
  Navigate,
} from 'react-router-dom';

import Home from './Pages/Home';
import Login from './Pages/Login';
import Register from './Pages/Register';
import Cart from './Pages/Cart';
import ProductDetails from './Pages/ProductDetails';

import './App.css';

function ProtectedRoute({ children }) {
  const token = sessionStorage.getItem('jwtToken');

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  return children;
}

function AppLayout({
  darkMode,
  toggleTheme,
  cart,
  cartCount,
  addToCart,
  removeFromCart,
  updateQuantity,
}) {
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
            <ProtectedRoute>
              <Home
                cartCount={cartCount}
                addToCart={addToCart}
                darkMode={darkMode}
                toggleTheme={toggleTheme}
              />
            </ProtectedRoute>
          }
        />

        <Route
          path="/product/:id"
          element={
            <ProtectedRoute>
              <ProductDetails addToCart={addToCart} />
            </ProtectedRoute>
          }
        />

        <Route path="/login" element={<Login />} />

        <Route path="/register" element={<Register />} />

        <Route
          path="/cart"
          element={
            <ProtectedRoute>
              <Cart
                cart={cart}
                removeFromCart={removeFromCart}
                updateQuantity={updateQuantity}
              />
            </ProtectedRoute>
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

      const currentQuantity = existingProduct
        ? existingProduct.quantity
        : 0;

      const availableStock = Number(product.stockQuantity);

      if (
        !Number.isFinite(availableStock) ||
        availableStock < 0
      ) {
        return previousCart;
      }

      if (currentQuantity >= availableStock) {
        alert('Sorry, available stock limit reached!');
        return previousCart;
      }

      if (existingProduct) {
        return previousCart.map((item) =>
          item.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      }

      return [
        ...previousCart,
        { ...product, quantity: 1 },
      ];
    });
  };

  const removeFromCart = (productId) => {
    setCart((previousCart) =>
      previousCart.filter((item) => item.id !== productId)
    );
  };

  const updateQuantity = (productId, quantity) => {
    const item = cart.find(
      (product) => product.id === productId
    );

    if (!item) {
      return;
    }

    if (quantity < 1) {
      removeFromCart(productId);
      return;
    }

    const availableStock = Number(item.stockQuantity);

    if (
      !Number.isFinite(availableStock) ||
      quantity > availableStock
    ) {
      alert('Sorry, available stock limit reached!');
      return;
    }

    setCart((previousCart) =>
      previousCart.map((product) =>
        product.id === productId
          ? { ...product, quantity }
          : product
      )
    );
  };

  const cartCount = cart.reduce(
    (total, item) => total + item.quantity,
    0
  );

  return (
    <div
      className={`app ${
        darkMode ? 'dark-mode' : 'light-mode'
      }`}
    >
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
