package com.tutorweb.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SYS_001(401,"Lỗi hệ thống"),
    SYS_002(402, "Tham số không hợp lệ (Lỗi validate)"),

    // Authentication / Authorization
    AUTH_003(403, "Đăng nhập thất bại (Sai email hoặc mật khẩu)"),
    AUTH_004(404, "Access Token không hợp lệ hoặc đã hết hạn"),
    AUTH_005(405, "Không có quyền truy cập (Sai role)"),
    AUTH_006(406, "Refresh Token không hợp lệ hoặc đã hết hạn"),

    // User
    USR_007(407, "UserName đã tồn tại"),
    USR_008(407, "Phone đã tồn tại"),
    USR_009(407, "Email đã tồn tại"),
    USR_010(408, "Không tìm thấy người dùng"),
    USR_011(409, "Tài khoản người dùng đã bị khóa"),

    // Tutor
    TUT_012(410, "Không tìm thấy hồ sơ gia sư"),
    TUT_013(411, "Hồ sơ gia sư chưa được phê duyệt"),

    // Booking
    BK_014(412,"Trùng lịch đặt"),
    BK_015(413, "Không tìm thấy lịch đặt"),
    BK_016(414, "Thời gian đặt không hợp lệ (Thời gian bắt đầu ≥ thời gian kết thúc)");
    private final int code;
    private final String message;
}
