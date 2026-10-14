
import { Link } from 'react-router-dom';
import '../App.css';

function Login() {
  return (
    <div className="auth-page">
      <form className="auth-card">
        <h2>Welcome Back!</h2>
        <p>Login to your ShopEase account</p>

        <label>Email</label>
        <input type="email" placeholder="Enter your email" required />

        <label>Password</label>
        <input type="password" placeholder="Enter your password" required />

        <button type="submit">Login</button>

        <p>
          Don't have an account? <Link to="/register">Register</Link>
        </p>
      </form>
    </div>
  );
}

export default Login;
