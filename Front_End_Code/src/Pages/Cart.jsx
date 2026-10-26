
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../App.css';

function Cart({ cart, removeFromCart, updateQuantity }) {
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const totalPrice = cart.reduce(
    (total, item) => total + item.price * item.quantity,
    0
  );

  const handlePlaceOrder = async () => {
    setMessage('');
    setError('');

    const userId = sessionStorage.getItem('userId');
    const token = sessionStorage.getItem('jwtToken');

    if (!userId || !token) {
      setError('Please log in again before placing your order.');
      return;
    }

    const orderPayload = {
      userId: Number(userId),
      totalAmount: totalPrice,
      items: cart.map((item) => ({
        productId: item.id,
        quantity: item.quantity,
        price: item.price,
        subtotal: item.price * item.quantity,
      })),
    };

    setLoading(true);

    try {
      const response = await fetch(
        'http://localhost:8083/order/create',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(orderPayload),
        }
      );

      const data = await response.json().catch(() => null);

      if (!response.ok) {
        throw new Error(
          data?.message || 'Unable to place order. Please try again.'
        );
      }

      cart.forEach((item) => removeFromCart(item.id));

      setMessage(
        `Order placed successfully! Order ID: ${data?.id ?? 'N/A'}`
      );
    } catch (err) {
      setError(
        err instanceof TypeError
          ? 'Cannot connect to the Order Service. Check that the backend is running and CORS is configured.'
          : err.message
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="cart-page">
      <h1>Shopping Cart</h1>

      {error && <p role="alert">{error}</p>}
      {message && <p role="status">{message}</p>}

      {cart.length === 0 ? (
        <div className="empty-cart">
          <h2>Your cart is empty!</h2>
          <p>Add some products to continue shopping.</p>
          <Link to="/" className="shop-button">
            Continue Shopping
          </Link>
        </div>
      ) : (
        <>
          <div className="cart-items">
            {cart.map((item) => (
              <div className="cart-item" key={item.id}>
                <div className="cart-item-image">{item.emoji}</div>

                <div className="cart-item-details">
                  <h3>{item.name}</h3>

                  <p>
                    Price: ₹{item.price.toLocaleString('en-IN')}
                  </p>

                  <div className="quantity-controls">
                    <button
                      type="button"
                      onClick={() =>
                        updateQuantity(item.id, item.quantity - 1)
                      }
                    >
                      −
                    </button>

                    <span>{item.quantity}</span>

                    <button
                      type="button"
                      onClick={() =>
                        updateQuantity(item.id, item.quantity + 1)
                      }
                    >
                      +
                    </button>
                  </div>

                  <p>
                    Subtotal: ₹
                    {(item.price * item.quantity).toLocaleString('en-IN')}
                  </p>

                  <button
                    type="button"
                    className="remove-button"
                    onClick={() => removeFromCart(item.id)}
                  >
                    Remove
                  </button>
                </div>
              </div>
            ))}
          </div>

          <div className="cart-summary">
            <h2>
              Total: ₹{totalPrice.toLocaleString('en-IN')}
            </h2>

            <button
              type="button"
              className="shop-button"
              onClick={handlePlaceOrder}
              disabled={loading}
            >
              {loading ? 'Placing Order...' : 'Place Order'}
            </button>

            <p>
              <Link to="/">Continue Shopping</Link>
            </p>
          </div>
        </>
      )}
    </main>
  );
}

export default Cart;
