
import { Link } from 'react-router-dom';
import '../App.css';

function Register() {
  return (
    <div className="auth-page">
      <form className="auth-card">
        <h2>Create Account</h2>
        <p>Join ShopEase today</p>

        <label>Full Name</label>
        <input type="text" placeholder="Enter your name" required />

        <label>Email</label>
        <input type="email" placeholder="Enter your email" required />

        <label>Password</label>
        <input
          type="password"
          placeholder="Create a password"
          required
          minLength={6}
        />

        <button type="submit">Register</button>

        <p>
          Already have an account? <Link to="/login">Login</Link>
        </p>
      </form>
    </div>
  );
}

export default Register;
