/*
 * Ticket 03 — missing-default-in-switch false positive when default: exists
 * Aqua check: missing-default-in-switch-aquasec-cpp
 * Semgrep rule id: missing-default-in-switch
 *
 * Verify (from repo root):
 *   semgrep --validate --config sast/aqua/cpp/missing-default-in-switch.yml
 *   semgrep --test --config sast/aqua/cpp/missing-default-in-switch.yml tests/sast/ticket-verification/ticket-03-missing-default-switch-has-default-fp.cpp
 *
 * Expect: All tests pass — positive control is ruleid; customer switch with default is ok (no FP).
 */

// --- Control: missing default (unresolved Color — same style as main test file) ---

void ticket03PositiveControlMissingDefault(Color color) {
    // ruleid: missing-default-in-switch
    switch (color) {
        case RED:
            (void)0;
            break;
        case GREEN:
            (void)0;
            break;
        case BLUE:
            (void)0;
            break;
    }
}

// --- Customer-style: many cases, nested bodies, default last without break ---

enum DataPointTypeTicket03 { BINARY_INPUT, ANALOG_INPUT, BINARY_OUTPUT, BINARY_COUNTER, FROZEN_COUNTER };


void ticket03CustomerStyleSwitchHasDefault(DataPointTypeTicket03 msg_pointType) {
    // ok: missing-default-in-switch
    switch (msg_pointType) {
        case BINARY_INPUT:
            if (opaque_ticket03()) {
                if (opaque_inner_ticket03()) {
                    do_work_ticket03();
                } else {
                    err_ticket03();
                }
            } else {
                err_ticket03();
            }
            break;

        case ANALOG_INPUT:
            if (opaque_ticket03()) {
                do_work_ticket03();
            } else {
                err_ticket03();
            }
            break;

        case BINARY_OUTPUT:
            do_output_ticket03();
            break;

        case BINARY_COUNTER:
            if (opaque_ticket03()) {
                do_work_ticket03();
            }
            break;

        case FROZEN_COUNTER:
            if (opaque_ticket03()) {
                do_work_ticket03();
            }
            break;

        default:
            err_unknown_ticket03(static_cast<int>(msg_pointType));
    }
}
