import { useState } from 'react';
import { Link } from 'react-router-dom';
import '../App.css';

const products = [
  {
    id: 1,
    name: 'iPhone 15',
    price: 69999,
    category: 'Smartphones',
    emoji: '📱',
  },
  {
    id: 2,
    name: 'Samsung Galaxy S25',
    price: 74999,
    category: 'Smartphones',
    emoji: '📲',
  },
  {
    id: 3,
    name: 'Sony WH-1000XM5',
    price: 29999,
    category: 'Headphones',
    emoji: '🎧',
  },
];

function Home({ cartCount, addToCart, darkMode, toggleTheme }) {
  const [search, setSearch] = useState('');
  const isLoggedIn = Boolean(sessionStorage.getItem('jwtToken'));

  const filteredProducts = products.filter((product) =>
    `${product.name} ${product.category}`
      .toLowerCase()
      .includes(search.toLowerCase().trim())
  );

  return (
    <div className="home-page">
      <nav className="navbar">
        
        <Link to="/" className="logo">
          ShopEase
        </Link>

        <input
          className="search"
          type="text"
          placeholder="Search products..."
          value={search}
          onChange={(event) => setSearch(event.target.value)}
        />

        
        <div className="nav-links">
          <a href="#products">Products</a>
          
          {isLoggedIn ? (
            <button
              type="button"
              className="logout-button"
              onClick={() => {
                sessionStorage.removeItem('jwtToken');
                window.location.href = '/login';
              }}
            >
              Logout
            </button>
          ) : (
            <>
              <Link to="/login">Login</Link>
              <Link to="/register">Register</Link>
            </>
          )}
        
          <Link to="/cart" className="cart-button">
            🛒 Cart ({cartCount})
          </Link>
        </div>


        <button
          type="button"
          className="theme-toggle"
          onClick={toggleTheme}
          aria-label="Toggle dark and light mode"
          aria-pressed={darkMode}>
          <span className="toggle-circle">
            {darkMode ? '🌙' : '☀️'}
          </span>
        </button>

      </nav>

      <section className="hero">
        <div>
          <p className="eyebrow">WELCOME TO SHOPEASE</p>
          <h1>
            Everything you need,
            <br />
            all in one place.
          </h1>
          <p>Discover products you love at prices you'll enjoy.</p>

          <a className="shop-button" href="#products">
            Shop Now
          </a>
        </div>

        <div className="hero-emoji">🛍️</div>
      </section>

      <main id="products" className="products-section">
        <div className="section-heading">
          <div>
            <h2>Our Products</h2>
            <p>Explore our featured collection</p>
          </div>

          <span>{filteredProducts.length} products</span>
        </div>

        {filteredProducts.length === 0 ? (
          <p>No products found. Try another search.</p>
        ) : (
          <div className="product-grid">
            {filteredProducts.map((product) => (
              <div className="product-card" key={product.id}>
                <div className="product-image">
                  {product.emoji}
                </div>

                <p className="category">{product.category}</p>
                <h3>{product.name}</h3>

                <p className="price">
                  ₹{product.price.toLocaleString('en-IN')}
                </p>

                <button
                  type="button"
                  className="add-button"
                  onClick={() => addToCart(product)}
                >
                  Add to Cart
                </button>
              </div>
            ))}
          </div>
        )}
      </main>

      <footer>
        <p>© 2026 ShopEase — E-Commerce Order Management System</p>
      </footer>
    </div>
  );
}
export default Home;
