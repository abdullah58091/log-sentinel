import { useState } from 'react'
import InteractiveBackground from '../components/InteractiveBackground'

function Register() {
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const handleRegister = async (event) => {
    event.preventDefault()

    try {
      const response = await fetch(
        'http://localhost:8080/api/auth/register',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username,
            email,
            password,
          }),
        }
      )

      if (!response.ok) {
        throw new Error('Registration failed')
      }

      alert('Registration successful! You can now login.')

      window.location.href = '/login'
    } catch (error) {
      alert('Registration failed. Please check your details.')
    }
  }

  return (
    <div className="relative min-h-screen overflow-hidden">

      {/* Magic Background */}
      <InteractiveBackground />

      {/* Register Form */}
      <div className="relative z-10 min-h-screen flex items-center justify-center px-4 py-8">

        <div className="w-full max-w-md bg-gray-900/80 backdrop-blur-md border border-purple-500/40 rounded-2xl shadow-2xl p-8">

          <div className="text-center">
            <h1 className="text-3xl font-bold text-white">
              Log Sentinel
            </h1>

            <p className="mt-2 text-gray-300">
              Create your account
            </p>
          </div>

          <form
            onSubmit={handleRegister}
            className="mt-8 space-y-5"
          >

            <div>
              <label className="block text-sm font-medium text-gray-200">
                Username
              </label>

              <input
                type="text"
                value={username}
                onChange={(event) => setUsername(event.target.value)}
                placeholder="Enter your username"
                className="mt-2 w-full px-4 py-3 bg-gray-800/80 text-white placeholder-gray-400 border border-purple-500/40 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-200">
                Email
              </label>

              <input
                type="email"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                placeholder="Enter your email"
                className="mt-2 w-full px-4 py-3 bg-gray-800/80 text-white placeholder-gray-400 border border-purple-500/40 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-200">
                Password
              </label>

              <input
                type="password"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                placeholder="Create a password"
                className="mt-2 w-full px-4 py-3 bg-gray-800/80 text-white placeholder-gray-400 border border-purple-500/40 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                required
              />
            </div>

            <button
              type="submit"
              className="w-full bg-gradient-to-r from-purple-600 to-pink-600 text-white py-3 rounded-lg font-semibold hover:from-purple-700 hover:to-pink-700 transition-all duration-300 shadow-lg"
            >
              Create Account
            </button>

          </form>

          <p className="mt-6 text-center text-sm text-gray-300">
            Already have an account?{' '}

            <a
              href="/login"
              className="text-purple-400 font-medium hover:text-pink-400 hover:underline"
            >
              Login
            </a>
          </p>

        </div>
      </div>
    </div>
  )
}

export default Register