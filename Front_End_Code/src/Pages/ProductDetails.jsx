
import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import '../App.css';

function ProductDetails({ addToCart }) {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        setLoading(true);
        setError('');

        const response = await fetch(
          `http://localhost:8083/product/id/${id}`
        );

        if (!response.ok) {
          throw new Error('Product not found.');
        }

        const data = await response.json();
        setProduct(data);
      } catch (err) {
        setError(err.message || 'Unable to load product.');
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  if (loading) {
    return <main className="product-details"><p>Loading product...</p></main>;
  }

  if (error || !product) {
    return (
      <main className="product-details">
        <p role="alert">{error || 'Product not found.'}</p>
        <Link to="/">Back to Products</Link>
      </main>
    );
  }

  return (
    <main className="product-details">
      <Link to="/">← Back to Products</Link>

      <div className="product-details-card">
        <div className="product-details-image">
          {product.category?.toLowerCase() === 'headphones'
            ? '🎧'
            : '📱'}
        </div>

        <div className="product-details-info">
          <p className="category">{product.category}</p>
          <h1>{product.name}</h1>
          <p>{product.description}</p>

          <h2 className="price">
            ₹{Number(product.price).toLocaleString('en-IN')}
          </h2>

          <p>Available Stock: {product.stockQuantity}</p>

          <button
            type="button"
            className="add-button"
            disabled={product.stockQuantity <= 0}
            onClick={() => addToCart(product)}
          >
            {product.stockQuantity <= 0
              ? 'Out of Stock'
              : 'Add to Cart'}
          </button>

          <p>
            <Link to="/cart">View Cart</Link>
          </p>
        </div>
      </div>
    </main>
  );
}

export default ProductDetails;
