import React from 'react';
import { Link } from 'react-router-dom';
import '../App.css';

function Cart({ cart, removeFromCart, updateQuantity }) {
  const totalPrice = cart.reduce(
    (total, item) => total + item.price * item.quantity,
    0
  );

  return (
    <main className="cart-page">
      <h1>Shopping Cart</h1>

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
                <div className="cart-item-image">
                  {item.emoji}
                </div>

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

            <Link to="/" className="shop-button">
              Continue Shopping
            </Link>
          </div>
        </>
      )}
    </main>
  );
}

export default Cart;
