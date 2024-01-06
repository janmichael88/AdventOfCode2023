from math import prod
with open("input.txt") as fin:
    raw_workflows, raw_variables = fin.read().split('\n\n')

raw_workflows = raw_workflows.splitlines()
raw_variables = raw_variables.splitlines()

def parse_workflows(lines):
    workflows = {}

    for line in lines:
        name = line[:line.find('{')]
        rules = line[line.find('{') + 1:-1].split(',')
        # Separate the last rule (name only) from the rest
        rules, last = rules[:-1], rules[-1]
        # ['expr1:name1', ...] -> [[expr1, name1], ...]
        rules = (r.split(':') for r in rules)
        # [[expr1, name1], ...] -> [(var_name, op, value, next_workflow_name), ...]
        rules = [(exp[0], exp[1], int(exp[2:]), nxt) for exp, nxt in rules]
        workflows[name] = (rules, last)

    return workflows

def parse_variables(lines):
    for line in lines:
        # '{a=123,b=456}' -> ['a=123', 'b=456']
        assignments = line[1:-1].split(',')
        # ['a=123', 'b=456'] -> [('a', '123'), ('b', '456')]
        assignments = map(lambda a: a.split('='), assignments)
        # [('a', '123'), ('b', '456')] -> {'a': 123, 'b': 456}
        yield {a[0]: int(a[1]) for a in assignments}

workflows = parse_workflows(raw_workflows)
variables = parse_variables(raw_variables)

def run(workflows, variables):
    cur = 'in'

    while cur != 'A' and cur != 'R':
        rules, last = workflows[cur]

        for varname, op, value, nxt in rules:
            var = variables[varname]
            if op == '<' and var < value:
                cur = nxt
                break
            if op == '>' and var > value:
                cur = nxt
                break
        else:
            # No break statement encountered (i.e., none of the rules matched)
            cur = last

    if cur == 'A':
        return sum(variables.values())
    return 0

total = sum(run(workflows, v) for v in variables)
print(total)

#part2
def count_accepted(workflows, ranges, cur='in'):
	if cur == 'A':
		return prod(hi - lo + 1 for lo, hi in ranges.values())

	if cur == 'R':
		return 0

	rules, last = workflows[cur]
	total = 0

	for var, op, value, nxt in rules:
		lo, hi = ranges[var]

		if op == '<':
			if lo < value:
				total += count_accepted(workflows, ranges.update({var: (lo, value - 1)}), nxt)

			ranges[var] = (max(lo, value), hi)
		else:
			if hi > value:
				total += count_accepted(workflows, ranges.update({var: (value + 1, hi)}), nxt)

			ranges[var] = (lo, min(hi, value))

	total += count_accepted(workflows, ranges, last)
	return total
accepted = count_accepted(workflows, {v: (1, 4000) for v in 'xmas'})
print('Part 2:', accepted)
