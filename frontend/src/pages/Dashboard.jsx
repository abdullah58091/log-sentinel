import { useEffect, useState } from 'react'

function Dashboard() {
  const [stats, setStats] = useState([
    { title: 'Total Logs', value: 0 },
    { title: 'Errors', value: 0 },
    { title: 'Critical Logs', value: 0 },
    { title: 'Open Incidents', value: 0 },
    { title: 'Resolved Incidents', value: 0 },
  ])

  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    const fetchDashboardSummary = async () => {
      try {
        const token = localStorage.getItem('token')

        const response = await fetch(
            'http://localhost:8080/api/dashboard/summary',
            {
              method: 'GET',
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
        )

        if (!response.ok) {
          throw new Error('Failed to fetch dashboard data')
        }

        const data = await response.json()

        setStats([
          { title: 'Total Logs', value: data.totalLogs },
          { title: 'Errors', value: data.errors },
          { title: 'Critical Logs', value: data.critical },
          { title: 'Open Incidents', value: data.openIncidents },
          { title: 'Resolved Incidents', value: data.resolvedIncidents },
        ])
      } catch (error) {
        setError('Unable to load dashboard data.')
      } finally {
        setLoading(false)
      }
    }

    fetchDashboardSummary()
  }, [])

  return (
      <div className="p-8">

        <div>
          <h1 className="text-3xl font-bold text-gray-800">
            Dashboard
          </h1>

          <p className="mt-2 text-gray-600">
            Overview of your log monitoring and incident management system.
          </p>
        </div>

        {loading && (
            <p className="mt-8 text-gray-500">
              Loading dashboard...
            </p>
        )}

        {error && (
            <p className="mt-8 text-red-600">
              {error}
            </p>
        )}

        {!loading && !error && (
            <div className="mt-8 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 gap-6">

              {stats.map((stat) => (
                  <div
                      key={stat.title}
                      className="bg-white rounded-xl shadow p-6"
                  >
                    <p className="text-sm text-gray-500">
                      {stat.title}
                    </p>

                    <p className="mt-3 text-3xl font-bold text-gray-800">
                      {stat.value}
                    </p>
                  </div>
              ))}

            </div>
        )}

      </div>
  )
}

export default Dashboard