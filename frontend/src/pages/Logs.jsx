import { useEffect, useState } from 'react'

function Logs() {
    const [logs, setLogs] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')

    useEffect(() => {
        const fetchLogs = async () => {
            try {
                const token = localStorage.getItem('token')

                const response = await fetch(
                    'http://localhost:8080/api/logs',
                    {
                        method: 'GET',
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                )

                if (!response.ok) {
                    throw new Error('Failed to fetch logs')
                }

                const data = await response.json()

                setLogs(data)
            } catch (error) {
                setError('Unable to load logs.')
            } finally {
                setLoading(false)
            }
        }

        fetchLogs()
    }, [])

    return (
        <div className="min-h-screen bg-gray-100 p-8">

            <div>
                <h1 className="text-3xl font-bold text-gray-800">
                    Logs
                </h1>

                <p className="mt-2 text-gray-600">
                    View and analyze application logs.
                </p>
            </div>

            {loading && (
                <p className="mt-8 text-gray-500">
                    Loading logs...
                </p>
            )}

            {error && (
                <p className="mt-8 text-red-600">
                    {error}
                </p>
            )}

            {!loading && !error && logs.length === 0 && (
                <div className="mt-8 bg-white rounded-xl shadow p-6">
                    <p className="text-gray-500">
                        No logs available yet.
                    </p>
                </div>
            )}

            {!loading && !error && logs.length > 0 && (
                <div className="mt-8 bg-white rounded-xl shadow overflow-hidden">

                    <div className="overflow-x-auto">

                        <table className="w-full text-left">

                            <thead className="bg-gray-800 text-white">

                            <tr>
                                <th className="px-6 py-4">
                                    ID
                                </th>

                                <th className="px-6 py-4">
                                    Level
                                </th>

                                <th className="px-6 py-4">
                                    Message
                                </th>

                                <th className="px-6 py-4">
                                    Source
                                </th>

                                <th className="px-6 py-4">
                                    Timestamp
                                </th>
                            </tr>

                            </thead>

                            <tbody>

                            {logs.map((log) => (
                                <tr
                                    key={log.id}
                                    className="border-b border-gray-200 hover:bg-gray-50"
                                >

                                    <td className="px-6 py-4 text-gray-700">
                                        {log.id}
                                    </td>

                                    <td className="px-6 py-4">
                      <span className="font-medium">
                        {log.level}
                      </span>
                                    </td>

                                    <td className="px-6 py-4 text-gray-700">
                                        {log.message}
                                    </td>

                                    <td className="px-6 py-4 text-gray-700">
                                        {log.source}
                                    </td>

                                    <td className="px-6 py-4 text-gray-700">
                                        {log.timestamp}
                                    </td>

                                </tr>
                            ))}

                            </tbody>

                        </table>

                    </div>

                </div>
            )}

        </div>
    )
}

export default Logs