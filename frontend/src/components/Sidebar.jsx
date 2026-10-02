import { Link } from 'react-router-dom'

function Sidebar() {
  return (
    <aside className="w-64 min-h-screen bg-gray-800 text-white p-5">
      <h2 className="text-lg font-semibold mb-6">
        Navigation
      </h2>

      <nav className="space-y-2">
        <Link
          to="/dashboard"
          className="block px-4 py-2 rounded-lg hover:bg-gray-700"
        >
          Dashboard
        </Link>

        <Link
          to="/logs"
          className="block px-4 py-2 rounded-lg hover:bg-gray-700"
        >
          Logs
        </Link>

        <Link
          to="/incidents"
          className="block px-4 py-2 rounded-lg hover:bg-gray-700"
        >
          Incidents
        </Link>
      </nav>
    </aside>
  )
}

export default Sidebar