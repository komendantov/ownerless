import json


def get_matrix():
    with open("dataBinary.json") as data:
        data = dict(json.loads(data.read())["matrix"])
        vertex_count = len(data)

        matrix = [[0 for j in range(vertex_count)]for i in range(vertex_count)]

        for k, v in data.items():
            for element in v:
                matrix[int(k)][int(element)] = 1
        for row in matrix:
            print(row)
    return matrix


matrix = get_matrix()


def find_ways(way, end, end_ways):
    for i in range(len(matrix)):
        if matrix[way[-1]][i] == 0:
            continue
        if i in way:
            continue
        if i == end:
            end_ways += [way + [i]]
            break
        cur_way = find_ways(way + [i], end, end_ways)
        if cur_way is None:
            continue
    return end_ways


def find_min_way(ways):
    if len(ways) == 0:
        return None
    min_length = len(ways[0])
    min_way = ways[0]
    for way in ways:
        if min_length > len(way):
            min_length = len(way)
            min_way = way
    return min_way


all_ways = {}


for j in range(1, len(matrix)):
    end_ways = []
    all_ways["0 -> " + str(j)] = find_min_way(find_ways([0], j, end_ways))

with open("waysBinary.json", "w") as output:
    output.write(json.dumps(all_ways))
