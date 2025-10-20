#!/bin/sh
# wait-for-it.sh: wait until a host:port is available

host="$1"
shift
port="$1"
shift

echo "Waiting for $host:$port to be available..."

while ! nc -z "$host" "$port" >/dev/null 2>&1; do
  sleep 2
done

echo "$host:$port is now available!"

exec "$@"