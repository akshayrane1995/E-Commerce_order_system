
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../App.css';

function Home({ cartCount, addToCart, darkMode, toggleTheme }) {
  const [search, setSearch] = useState('');
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const isLoggedIn = Boolean(sessionStorage.getItem('jwtToken'));

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        setError('');

        const response = await fetch(
          'http://localhost:8083/product'
        );

        if (!response.ok) {
          throw new Error('Failed to fetch products.');
        }

        const data = await response.json();
        setProducts(data);
      } catch (err) {
        setError(err.message || 'Unable to load products.');
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  const filteredProducts = products.filter((product) =>
    `${product.name} ${product.category}`
      .toLowerCase()
      .includes(search.toLowerCase().trim())
  );

  return (
    <div className="home-page">
      <nav className="navbar">
        <Link to="/" className="logo">ShopEase</Link>

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
          aria-pressed={darkMode}
        >
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

          {!loading && !error && (
            <span>{filteredProducts.length} products</span>
          )}
        </div>

        {loading ? (
          <p>Loading products...</p>
        ) : error ? (
          <p role="alert">
            {error} Please check API Gateway and CORS configuration.
          </p>
        ) : filteredProducts.length === 0 ? (
          <p>No products found. Try another search.</p>
        ) : (
          <div className="product-grid">
            {filteredProducts.map((product) => (
              <div className="product-card" key={product.id}>
                <div className="product-image">
                  {product.category?.toLowerCase() === 'headphones'
                    ? '🎧'
                    : '📱'}
                </div>
                <Link
                    to={`/product/${product.id}`}
                    className="shop-button"> View Details
                </Link>

                <p className="category">{product.category}</p>
                <h3>{product.name}</h3>
                <p>{product.description}</p>

                <p className="price">
                  ₹{Number(product.price).toLocaleString('en-IN')}
                </p>

                <p>Stock: {product.stockQuantity}</p>

                <button
                  type="button"
                  className="add-button"
                  disabled={product.stockQuantity <= 0}
                  onClick={() =>
                    addToCart({
                      ...product,
                      emoji:
                        product.category?.toLowerCase() === 'headphones'
                          ? '🎧'
                          : '📱'
                    })
                  }>
                  {product.stockQuantity <= 0
                    ? 'Out of Stock'
                    : 'Add to Cart'}
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
